// This good for populate the data first before rendering the page
import { error } from '@sveltejs/kit';
const comments = [
	{
		slug: 'article-1',
		comments: [
			{
				id: 1,
				text: 'Comment 1 in Article 1'
			},
			{
				id: 2,
				text: 'Comment 2    in Article 1'
			}
		]
	},
	{
		slug: 'article-2',
		comments: [
			{
				id: 1,
				text: 'Comment 1 in Article 2'
			},
			{
				id: 2,
				text: 'Comment 2 in Article 2'
			}
		]
	}
];

export const load = async ({ params }) => {
	console.log('page: ', params);
	const articleComments = comments.find((article) => article.slug === params.slug);

	if (articleComments) {
		return {
			comments: articleComments.comments
		};
	}

	throw new error(404, 'Article comment not found');
};
