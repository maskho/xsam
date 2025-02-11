import LINKS from '$lib/data/form-list';

export function load() {
	return {
		summaries: LINKS.map((form) => {
			return {
				slug: form.slug,
				title: form.title,
				submission: form.submission
			};
		})
	};
}
