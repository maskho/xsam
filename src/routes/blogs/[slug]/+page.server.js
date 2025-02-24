import { error } from '@sveltejs/kit';

const articles = [
	{
		slug: 'article-1',
		title: 'Article 1',
		content: 'Content 1'
	},
	{
		slug: 'article-2',
		title: 'Article 2',
		content: 'Content 2'
	}
];

export const load = async ({ params }) => {
	console.log('page.server: ', params);
	const article = articles.find((article) => article.slug === params.slug);

	if (article) {
		return {
			title: article.title,
			content: article.content
		};
	}

	throw new error(404, 'Article not found');
};
