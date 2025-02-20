<script>
	import UserInput from '$lib/components/tutorials/UserInput.svelte';

	let data = $props();
	$inspect(data);
</script>

<UserInput username={'Kobar'}><h1>Halo Child!</h1></UserInput>
