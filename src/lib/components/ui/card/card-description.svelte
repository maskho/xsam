<script>
	import { cn } from '$lib/utils.js';

	let { ref = $bindable(null), class: className, children, ...restProps } = $props();
</script>

<p bind:this={ref} class={cn('!mt-3 text-sm font-base text-mtext', className)} {...restProps}>
	{@render children?.()}
</p>
