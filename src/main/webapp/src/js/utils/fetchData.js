export async function fetchData(url) {
  try {
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error('Failed to fetch data');
    }
    const data = await response.json();
    return {status: response.status, response: data};
  } catch (error) {
    console.error('Error fetching data:', error);
    return null;
  }
}

export async function postData(url, data, type = 'POST') {
  try {
    const response = await fetch(url, {
      method: type, headers: {
        'Content-Type': 'application/json'
      }, ...data && ({body: JSON.stringify(data)})
    });

    if (!response.ok) {
      throw new Error('Failed to post data');
    }
    const responseData = await response.json();
    return {status: response.status, response: responseData};
  } catch (error) {
    console.error('Error posting data:', error);
    throw new Error('Failed to post data');
    return null;
  }
}

