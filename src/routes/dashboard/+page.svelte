<script>
	import FormList from '$lib/components/FormList.svelte';
	import * as Card from '$lib/components/ui/card';

	let { data } = $props();
</script>

<div
	class="dark:text-darkText relative mx-auto h-full w-[700px] max-w-full p-8 text-text md:p-16 xl:w-[1400px]"
>
	<FormList {data} />
</div>
