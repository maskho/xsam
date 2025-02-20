<script>
	let username = $state('');

	$effect(() => {
		console.log('effect running');

		if (username) {
			console.log('username changed to', username);
		}
	});

	$inspect('inspect username', username);
</script>

<h1>Your username</h1>
<input type="text" bind:value={username} />

<p>{username}</p>

<style>
	p {
		color: red;
	}
</style>
