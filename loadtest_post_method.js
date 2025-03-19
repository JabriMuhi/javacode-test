import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
    stages: [
        { duration: '30s', target: 500 },
        { duration: '1m', target: 2000 },
        { duration: '30s', target: 0 },
    ],
};

export default function () {
    const url = 'http://localhost:8080/api/v1/wallet';
    const payload = JSON.stringify({
        wallet_id: '550e8400-e29b-41d4-a716-446655440000',
        operation_type: 'DEPOSIT',
        amount: 10,
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const res = http.post(url, payload, params);

    check(res, {
        'is status 200': (r) => r.status === 200,
        'is status not 500': (r) => r.status !== 500,
    });

    sleep(1);
}
