<script>
	import { cn } from '$lib/utils.js';

	let { ref = $bindable(null), value = $bindable(), class: className, ...restProps } = $props();
</script>

<input
	bind:this={ref}
	class={cn(
		'flex h-10 w-full rounded-base border-2 border-border bg-bw px-3 py-2 text-sm font-base text-text ring-offset-white selection:bg-main selection:text-mtext file:border-0 file:bg-transparent file:text-sm file:font-base focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-black focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50',
		className
	)}
	bind:value
	{...restProps}
/>
