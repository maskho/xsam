<script lang="ts">
	import Sun from 'lucide-svelte/icons/sun';
	import Moon from 'lucide-svelte/icons/moon';

	import { toggleMode } from 'mode-watcher';
	import Button from './ui/button/button.svelte';
</script>

<Button
	aria-label="Toggle theme"
	onclick={toggleMode}
	size="icon"
	variant="default"
	class="absolute right-8 top-8 md:right-16 md:top-16 md:h-12 md:w-12"
>
	<Sun
		class="h-[1.2rem] w-[1.2rem] rotate-0 scale-100 transition-all dark:-rotate-90 dark:scale-0"
	/>
	<Moon
		class="absolute h-[1.2rem] w-[1.2rem] rotate-90 scale-0 transition-all dark:rotate-0 dark:scale-100"
	/>
</Button>
