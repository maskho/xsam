<script>
	import { cn } from '$lib/utils.js';

	let { ref = $bindable(null), class: className, children, ...restProps } = $props();
</script>

<div
	bind:this={ref}
	class={cn('rounded-base border-2 border-border bg-main text-mtext shadow-shadow', className)}
	{...restProps}
>
	{@render children?.()}
</div>
