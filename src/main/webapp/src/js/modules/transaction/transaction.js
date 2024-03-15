import DataTable from 'datatables.net-bs5';
import {fetchData, postData} from '../../utils/fetchData';
import {API_URL} from '../../constants';
import {Toast} from '../../utils/toast';
import Choices from 'choices.js';

var customerChoice;
var productChoices;
var products;
var selectedProducts = [];


const initialize = () => {
  getData().catch(e => alert('fetch error'));
  setupDropdowns();
  document.getElementById('addPrd').addEventListener('click', function () {
    const productSelect = productChoices.getValue();
    const amount = document.querySelector('#amount').value;
    const product = products.find(e => e.id === +productSelect.value);

    if (amount > product.stock) {
      return Toast.fire({title: 'Amount is greater than stock', icon: 'warning'});
    }
    const productName = product.name;
    const index = selectedProducts.findIndex(e => e.productId === productSelect.value);
    if (index >= 0) {
      selectedProducts.at(index).quantity = Number(selectedProducts.at(index).quantity) + Number(amount);
      selectedProducts.at(index).total = Number(selectedProducts.at(index).quantity) * selectedProducts.at(index).price;
    } else {
      selectedProducts.push({
        productId: productSelect.value,
        quantity: amount,
        productName,
        total: amount * product.price,
        price: product.price
      });
    }
    showProducts();
  });

  document.getElementById('orderBtn').addEventListener('click', async function (ev) {
    try {
      if (selectedProducts.length === 0) {
        await Toast.fire({title: 'Select product', icon: 'warning'});
        return;
      }
      const addPrd = document.querySelector('#addPrd');
      ev.target.disabled = true;
      addPrd.disabled = true;
      const {response: responseCustomer} = await postData(API_URL.concat('/transaction'), {
        customerId: customerChoice.getValue().value,
        lineItems: selectedProducts
      });
      addPrd.disabled = false;
      ev.target.disabled = false;
      selectedProducts = [];
      showProducts();
      await getData();
      await Toast.fire({title: 'Transaction created', icon: 'success'});
    } catch (e) {
      addPrd.disabled = false;
      ev.target.disabled = false;
      await Toast.fire({title: 'Failed', icon: 'error'});
    }

  });
  document.getElementById('resetItems').addEventListener('click', function (ev) {
    selectedProducts = [];
    showProducts();
  });
};

const showProducts = (data = selectedProducts, id = 'productTableBody') => {
  const tableBody = document.getElementById(id);
  tableBody.innerHTML = '';
  let totalSum = 0;

  data.forEach(product => {
    const row = document.createElement('tr');

    const productNameCell = document.createElement('td');
    productNameCell.textContent = product.productName;

    const quantityCell = document.createElement('td');
    quantityCell.textContent = product.quantity;

    const totalCell = document.createElement('td');
    totalCell.textContent = product.total.toFixed(2);

    row.appendChild(productNameCell);
    row.appendChild(quantityCell);
    row.appendChild(totalCell);

    tableBody.appendChild(row);

    totalSum += parseFloat(product.total);
  });

  const totalRow = document.createElement('tr');
  const totalLabelCell = document.createElement('td');
  totalLabelCell.textContent = 'Total Sum:';
  totalLabelCell.setAttribute('colspan', '2'); // Span across two columns
  totalRow.appendChild(totalLabelCell);

  const totalValueCell = document.createElement('td');
  totalValueCell.textContent = totalSum.toFixed(2); // Format the total sum
  totalRow.appendChild(totalValueCell);

  tableBody.appendChild(totalRow);
};


const setupDropdowns = async () => {
  const supplier = document.querySelector('.product-select');
  const customer = document.querySelector('#customer');
  const {response} = await fetchData(API_URL.concat('/product'));
  const {response: responseCustomer} = await fetchData(API_URL.concat('/customer'));
  products = response.data;
  const data = response.data.map(e => ({label: e.name, value: e.id}));
  const dataCustomer = responseCustomer.data.map(e => ({label: e.name, value: e.id}));
  productChoices = new Choices(supplier, {choices: data});
  customerChoice = new Choices(customer, {choices: dataCustomer});
};


const renderDataTable = (data) => {
  new DataTable('#example').destroy();
  const columns = [
    {data: 'id', title: 'ID'},
    {data: 'customer.name', title: 'Customer'},
    {data: 'totalItems', title: 'Items'},
    {data: 'total', title: 'Total'},
    {data: 'createdAt', title: 'Created At'},
    {data: 'updatedAt', title: 'Updated At'},
    {
      title: 'Actions',
      render: function (data, type, row) {
        return `<div class="d-flex flex-column flex-md-row gap-2">
                            <button class="btn btn-primary btn-sm edit-btn edit" data-id="${row.id}">Edit</button>
                        </div>`;
      }
    }
  ];

  new DataTable('#example', {
    data: data,
    columns: columns,
    drawCallback: function () {
      const editButtons = document.querySelectorAll('.edit');
      const modal = new bootstrap.Modal(document.querySelector('#info-modal'));
      editButtons.forEach(button => {
        button.addEventListener('click', event => {
          const name = document.querySelector('#customer-name');
          const dataId = button.getAttribute('data-id');
          const record = data.find(e => e.id === +dataId);
          name.innerHTML = `Customer: ${record.customer.name}`;

          const items = record.transactionProducts.map(e => ({
            productName: e.product.name,
            quantity: e.quantity,
            price: e.product.price,
            total: e.product.price * e.quantity
          }));
          showProducts(items, 'productTableInfoBody');
          modal.toggle();
        });
      });
    }
  });
};


const getData = async () => {
  const tableContainer = document.querySelector('#example');
  tableContainer.innerHTML = 'loading...';
  try {
    const {response} = await fetchData(API_URL.concat('/transaction'));
    const formattedData = response.data.map(item => {
      const createdAtDate = new Date(item.createdAt);
      const updatedAtDate = new Date(item.updatedAt);
      const total = item.total.toFixed(2);
      const totalItems = item.transactionProducts.length;
      const formattedCreatedAt = createdAtDate.toLocaleString();
      const formattedUpdatedAt = updatedAtDate.toLocaleString();

      return {
        ...item, createdAt: formattedCreatedAt, updatedAt: formattedUpdatedAt, total, totalItems
      };
    });
    tableContainer.innerHTML = '';
    renderDataTable(formattedData);
  } catch (error) {
    tableContainer.innerHTML = 'error';
    console.error('Error fetching data:', error);
    renderLoadingError();
  }
};

const renderLoadingError = () => {
  const errorMessage = document.createElement('p');
  errorMessage.textContent = 'Failed to fetch data. Please try again later.';
  const tableContainer = document.querySelector('#table-container');
  tableContainer.innerHTML = '';
  tableContainer.appendChild(errorMessage);
};

document.addEventListener('DOMContentLoaded', () => initialize());
