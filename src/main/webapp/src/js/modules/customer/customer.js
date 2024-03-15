import DataTable from 'datatables.net-bs5';
import {fetchData, postData} from '../../utils/fetchData';
import {API_URL} from '../../constants';
import {disableForm, enableForm, serializeForm} from '../../utils/form';
import {Toast} from '../../utils/toast';
import Swal from 'sweetalert2';

const initialize = () => {
  getData().catch(e => alert('fetch error'));
  formValidation();
};

const formValidation = () => {
  const form = document.querySelector('#create-customer-form');

  form.addEventListener('submit', async (event) => {
    if (!form.checkValidity()) {
      event.preventDefault();
      event.stopPropagation();
      form.classList.add('was-validated');
      return;
    }
    event.preventDefault();
    const type = form.getAttribute('data-action') === 'create' ? 'POST' : 'PUT';
    const id = form.getAttribute('data-id');
    const route = type === 'POST' ? '/customer' : `/customer/${id}`;
    const formData = serializeForm(form);
    try {
      disableForm(form);
      await postData(API_URL.concat(route), {name: formData['customer-name']}, type);
      if (type === 'POST') form.reset();
      await getData();
      enableForm(form);
      await Toast.fire({title: 'Customer created', icon: 'success'});
    } catch (e) {
      enableForm(form);
      await Toast.fire({title: 'Creation failed', icon: 'error'});
    }
  });
};

const renderDataTable = (data) => {
  new DataTable('#example').destroy();
  const columns = [{data: 'id', title: 'ID'}, {data: 'name', title: 'Name'}, {
    data: 'transactionCount',
    title: 'Transactions'
  }, {data: 'createdAt', title: 'Created At'}, {data: 'updatedAt', title: 'Updated At'}, {
    title: 'Actions', render: function (data, type, row) {
      return `<div class="d-flex flex-column flex-md-row gap-2">
                            <button class="btn btn-primary btn-sm edit-btn edit" data-id="${row.id}">Edit</button>
                            <button class="btn btn-danger btn-sm delete-btn delete" data-id="${row.id}">Delete</button>
                        </div>`;
    }
  }];

  new DataTable('#example', {
    data: data, columns: columns
  });
  const editButtons = document.querySelectorAll('.edit');
  editButtons.forEach(button => {
    button.addEventListener('click', event => {
      const name = document.querySelector('#customer-name');
      const form = document.querySelector('#create-customer-form');
      const dataId = button.getAttribute('data-id');
      const record = data.find(e => e.id === +dataId);
      name.value = record.name;
      const modal = new bootstrap.Modal(document.querySelector('#create-customer-modal'));
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
            await postData(API_URL.concat(`/customer/${dataId}`), null, 'DELETE');
            await getData();
            await Toast.fire({title: 'Deleted', icon: 'success'});
          } catch (e) {
            await Toast.fire({title: 'Delete failed', icon: 'error'});
          }
        }
      });
    });
  });
};


const getData = async () => {
  const tableContainer = document.querySelector('#example');
  tableContainer.innerHTML = 'loading...';
  try {
    const {response} = await fetchData(API_URL.concat('/customer'));
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
