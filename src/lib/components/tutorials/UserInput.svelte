<script>
	let { username, children, ...props } = $props();
</script>

<h1>Username: {username}</h1>
<input type="text" bind:value={username} />
{@render children()}

<style>
	h1 {
		color: blue;
	}
</style>
