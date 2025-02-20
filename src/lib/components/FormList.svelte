<script>
	import { writable } from 'svelte/store';
	import { Button } from './ui/button';
	import CreateOptionsDialog from './CreateOptionsDialog.svelte';

	let { data } = $props();
	const isOpen = writable(false);
	const openCreateOptionsDialog = () => isOpen.set(true);
	const closeCreateOptionsDialog = () => isOpen.set(false);
</script>

<div class="justify-end xl:flex">
	<div
		id="grid-container"
		class="grid w-full grid-cols-2 gap-10 text-text dark:text-text md:grid-cols-3 xl:w-1/2 xl:pb-16"
	>
		<Button variant="card" size="card" onclick={openCreateOptionsDialog}>
			<p class="mt-3 line-clamp-3 text-base font-heading">Buat Baru</p>
		</Button>

		{#each data.summaries as { slug, title, submission }}
			<Button variant="card" size="card" href="/forms/{slug}/edit">
				<p class="mt-3 line-clamp-3 text-base font-heading">
					{title}
				</p>
				{#if submission}
					<p class="mt-1 text-xs font-base">
						Submission: {submission}
					</p>
				{/if}
			</Button>
		{/each}
	</div>
</div>
