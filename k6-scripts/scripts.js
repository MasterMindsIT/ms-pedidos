import http from 'k6/http';
import { check, sleep } from 'k6';

export let options = {
  vus: 15, // usuarios virtuales simultáneos
  duration: '300s', // duración total
};

export default function () {
     const products = ['1', '2', '3'];

  // Elegir uno aleatorio
  const randomIndex = Math.floor(Math.random() * products.length);
  const selectedProduct = products[randomIndex];
  let payload = JSON.stringify({
    productId: selectedProduct,
    quantity: 2
  });

  let params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };

  let res = http.post('http://localhost:8080/api/orders', payload, params);

  check(res, {
    'status is 200': (r) => r.status === 200,
  });

  sleep(1);
}
