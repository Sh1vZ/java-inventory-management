import {fetchData} from '../../utils/fetchData';
import {API_URL} from '../../constants';

const initialize = () => {
  getData().catch(e => alert('fetch error'));
};


const getData = async () => {
  const customerTotal = document.querySelector('#customer-total');
  const txTotal = document.querySelector('#transaction-count-total');
  const productsTotal = document.querySelector('#products-total');
  const txSum = document.querySelector('#transaction-total');
  try {
    customerTotal.innerHTML = 'Loading...';
    txTotal.innerHTML = 'Loading...';
    productsTotal.innerHTML = 'Loading...';
    txSum.innerHTML = 'Loading...';
    const {response} = await fetchData(API_URL.concat('/dashboard'));
    customerTotal.innerHTML = response.data.customerCount;
    txTotal.innerHTML = response.data.transactionCount;
    productsTotal.innerHTML = response.data.productsCount;
    txSum.innerHTML = response.data.sumTransactions.toFixed(2);

  } catch (error) {
    console.error('Error fetching data:', error);
  }
};


document.addEventListener('DOMContentLoaded', () => initialize());
