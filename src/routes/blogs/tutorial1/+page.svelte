<script>
	// In svelte5 we use $state to create a reactive state
	let num = $state(0);
	let command = $derived(num === 0 ? "Why don't you click me?" : `You've clicked me ${num} times!`);
	let congratulation = $derived.by(() => congratulate(num));

	function onclick() {
		num++;
		console.log('clicked');
	}

	function congratulate(num) {
		return `Congratulations! You've clicked me ${num} times!`;
	}
</script>

<button {onclick}>Click me</button>

<p>{command}</p>
<p>{congratulation}</p>

<a href="/blogs/article-1">Article 1</a>
<a href="/blogs/article-2">Article 2</a>

<style>
	p {
		color: red;
	}
</style>
