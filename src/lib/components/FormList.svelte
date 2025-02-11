<script>
	import { Button } from './ui/button';

	let { data } = $props();
</script>

<div class="justify-end xl:flex">
	<div
		id="grid-container"
		class="w450:grid-cols-1 w450:gap-7 grid w-full grid-cols-2 gap-10 text-text dark:text-text md:grid-cols-3 xl:w-1/2 xl:pb-16"
	>
		{#each data.summaries as { slug, title, submission }}
			<Button {slug} variant="card" size="card" href="/forms/{slug}/edit">
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
