<script>
	let { data } = $props();
	$inspect(data);
	// title and content from page.server is overriden  by page. That's why it will only show comments
	let { comments } = data;
</script>

<ul>
	{#each comments as comment}
		<li>{comment.text}</li>
	{/each}
</ul>

<!-- Will not work on redirect between the same +page.svelte
Because the way that +page.js works is load before the page.svelte is rendered
But if page.svelte is already rendered, it will not be re-rendered -->
<a href="/blogs/article-2">Next page</a>
