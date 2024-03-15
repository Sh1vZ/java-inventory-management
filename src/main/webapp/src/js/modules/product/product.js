import DataTable from 'datatables.net-bs5';
import {fetchData, postData} from '../../utils/fetchData';
import {API_URL} from '../../constants';
import {disableForm, enableForm, serializeForm} from '../../utils/form';
import {Toast} from '../../utils/toast';
import Swal from 'sweetalert2';
import Choices from 'choices.js';

const colors = [{value: '', label: 'Choose...'}, {value: 'red', label: 'Red'}, {
  value: 'yellow',
  label: 'Yellow'
}, {value: 'green', label: 'Green'}];

const suppliers = [{value: '', label: 'Choose...'}, {value: 'technex', label: 'TechNex'}, {
  value: 'fashionhub',
  label: 'FashionHub'
}, {value: 'homeessentials', label: 'HomeEssentials'}];

const types = [{value: '', label: 'Choose...'}, {value: 'electronics', label: 'Electronics'}, {
  value: 'clothing',
  label: 'Clothing'
}, {value: 'furniture', label: 'Furniture'}];

const sizes = [{value: '', label: 'Choose...'}, {value: 'small', label: 'Small'}, {
  value: 'medium',
  label: 'Medium'
}, {value: 'large', label: 'Large'}, {value: 'xl', label: 'XL'}];

var supplierChoices;
var typeChoices;
var colorChoice;
var sizeChoice;
const initialize = () => {
  getData().catch(e => alert('fetch error'));
  formValidation();
  setupDropdowns();
};

const setupDropdowns = () => {
  const supplier = document.querySelector('#supplier');
  const color = document.querySelector('#color');
  const type = document.querySelector('#type');
  const size = document.querySelector('#size');
  supplierChoices = new Choices(supplier, {choices: suppliers});
  typeChoices = new Choices(type, {choices: types});
  colorChoice = new Choices(color, {choices: colors});
  sizeChoice = new Choices(size, {choices: sizes});
};
var modal = new bootstrap.Modal(document.querySelector('#create-customer-modal'));

const formValidation = () => {
  const form = document.querySelector('#create-customer-form');

  form.addEventListener('submit', async (event) => {
    if (!form.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
      form.classList.add('was-validated');
      const supplier = document.querySelector('#supplier');
      const color = document.querySelector('#color');
      const typeAtt = document.querySelector('#type');
      const sizeAtt = document.querySelector('#size');
      if (typeAtt.value === '') {
        await Toast.fire({title: 'Choose Type', icon: 'warning'});
        return;
      }
      if (color.value === '') {
        await Toast.fire({title: 'Choose color', icon: 'warning'});
        return;
      }
      if (supplier.value === '') {
        await Toast.fire({title: 'Choose supplier', icon: 'warning'});
        return;
      }
      if (sizeAtt.value === '') {
        await Toast.fire({title: 'Choose supplier', icon: 'warning'});
        return;
      }
      return;
    }

    event.preventDefault();
    const type = form.getAttribute('data-action') === 'create' ? 'POST' : 'PUT';
    const id = form.getAttribute('data-id');
    const route = type === 'POST' ? '/product' : `/product/${id}`;
    const formData = serializeForm(form);
    try {
      disableForm(form);
      await postData(API_URL.concat(route), {
        name: formData['name'],
        supplier: formData['supplier'],
        color: formData['color'],
        type: formData['type'],
        price: formData['price'],
        size: formData['size'],
        sku: formData['sku']
      }, type);
      if (type === 'POST') {
        resetForm();
      } else {
        modal.toggle();
        resetForm();
      }
      await getData();
      enableForm(form);
      await Toast.fire({title: 'Product created', icon: 'success'});
    } catch (e) {
      console.log(e);
      enableForm(form);
      await Toast.fire({title: 'Product failed', icon: 'error'});
    }
  });
};

const resetForm = () => {
  document.querySelector('#name').value = '';
  colorChoice.setChoiceByValue('');
  typeChoices.setChoiceByValue('');
  supplierChoices.setChoiceByValue('');
  sizeChoice.setChoiceByValue('');
  document.querySelector('#sku').value = '';
  document.querySelector('#price').value = '';
};
const renderDataTable = (data) => {
  new DataTable('#example').destroy();
  const columns = [{
    data: 'id', title: 'ID'
  }, {data: 'name', title: 'Name'}, {
    data: 'price', title: 'Price'
  }, {
    data: 'sku', title: 'SKU'
  }, {
    data: 'supplier', title: 'Supplier'
  }, {
    data: 'color', title: 'Color'
  }, {
    data: 'size', title: 'Size'
  }, {
    data: 'type', title: 'Type'
  }, {
    data: 'createdAt', title: 'Created At'
  }, {
    data: 'updatedAt', title: 'Updated At'
  }, {
    title: 'Actions', render: function (data, type, row) {
      return `<div class="d-flex flex-column flex-md-row gap-2">
                            <button class="btn btn-primary btn-sm edit-btn edit" data-id="${row.id}">Edit</button>
                            <button class="btn btn-danger btn-sm delete-btn delete" data-id="${row.id}">Delete</button>
                        </div>`;
    }
  }];

  new DataTable('#example', {
    data: data, columns: columns,
    drawCallback: function () {
      const editButtons = document.querySelectorAll('.edit');
      editButtons.forEach(button => {
        button.addEventListener('click', event => {
          const name = document.querySelector('#name');
          const sku = document.querySelector('#sku');
          const price = document.querySelector('#price');

          const form = document.querySelector('#create-customer-form');
          const dataId = button.getAttribute('data-id');
          const record = data.find(e => e.id === +dataId);
          name.value = record.name;
          sku.value = record.sku;
          price.value = record.price;
          colorChoice.setChoiceByValue(record.color ?? '');
          typeChoices.setChoiceByValue(record.type ?? '');
          supplierChoices.setChoiceByValue(record.supplier ?? '');
          sizeChoice.setChoiceByValue(record.size ?? '');
          form.setAttribute('data-action', 'update');
          form.setAttribute('data-id', dataId);
          modal.show();
        });
      });
      const deleteButtons = document.querySelectorAll('.delete');
      deleteButtons.forEach(button => {
        button.addEventListener('click', event => {
          const dataId = button.getAttribute('data-id');
          Swal.fire({
            title: 'Are you sure?',
            text: 'You won\'t be able to revert this!',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, delete it!'
          }).then(async (result) => {
            if (result.isConfirmed) {
              try {
                await postData(API_URL.concat(`/product/${dataId}`), null, 'DELETE');
                await getData();
                await Toast.fire({title: 'Deleted', icon: 'success'});
              } catch (e) {
                await Toast.fire({title: 'Delete failed', icon: 'error'});
              }
            }
          });
        });
      });
    }
  });
};


const getData = async () => {
  const tableContainer = document.querySelector('#example');
  tableContainer.innerHTML = 'loading...';
  try {
    const {response} = await fetchData(API_URL.concat('/product'));
    const formattedData = response.data.map(item => {
      const createdAtDate = new Date(item.createdAt);
      const updatedAtDate = new Date(item.updatedAt);

      const formattedCreatedAt = createdAtDate.toLocaleString();
      const formattedUpdatedAt = updatedAtDate.toLocaleString();

      return {
        ...item, createdAt: formattedCreatedAt, updatedAt: formattedUpdatedAt
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
