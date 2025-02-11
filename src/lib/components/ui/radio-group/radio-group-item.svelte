<script>
	import { RadioGroup as RadioGroupPrimitive } from 'bits-ui';
	import Circle from 'lucide-svelte/icons/circle';
	import { cn } from '$lib/utils.js';

	let { ref = $bindable(null), class: className, ...restProps } = $props();
</script>

<RadioGroupPrimitive.Item
	bind:ref
	class={cn(
		'aspect-square h-5 w-5 rounded-full border-2 border-border text-text ring-offset-white focus:outline-none focus-visible:ring-2 focus-visible:ring-black focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50',
		className
	)}
	{...restProps}
>
	{#snippet children({ checked })}
		<div class="flex items-center justify-center">
			{#if checked}
				<Circle class="size-2.5 fill-current text-current" />
			{/if}
		</div>
	{/snippet}
</RadioGroupPrimitive.Item>
