<script>
	import Tutorial3Form from './tutorial3-form.svelte';

	let { data } = $props();
</script>

<Tutorial3Form {data} />

<p>Data: {data.username}</p>
