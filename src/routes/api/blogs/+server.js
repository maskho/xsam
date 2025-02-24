import { json } from '@sveltejs/kit';

export const DELETE = async ({ request }) => {
	const body = await request.json();

	console.log(body);

	return json({ message: 'success' });
};
