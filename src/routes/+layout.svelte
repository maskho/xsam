<script>
	import ThemeSwitcher from '$lib/components/ThemeSwitcher.svelte';
	import '../app.css';
	import { ModeWatcher } from 'mode-watcher';
	import { onMount } from 'svelte';
	import { writable } from 'svelte/store';

	let { children } = $props();
	let container;
	const isOutside = writable(false);
	const isInactive = writable(false);
	let inactivityTimeout;

	// export const useMousePosition = () => {
	// 	let x = $state(0);
	// 	let y = $state(0);

	// 	const updateMousePosition = (event) => {
	// 		x = event.clientX;
	// 		y = event.clientY;
	// 		resetInactivityTimer();
	// 	};

	// 	const resetInactivityTimer = () => {
	// 		clearTimeout(inactivityTimeout);
	// 		isInactive.set(false);
	// 		inactivityTimeout = setTimeout(() => {
	// 			isInactive.set(true);
	// 		}, 10000);
	// 	};

	// 	$effect(() => {
	// 		window.addEventListener('mousemove', updateMousePosition);
	// 		window.addEventListener('keydown', resetInactivityTimer);
	// 		resetInactivityTimer();
	// 		return () => {
	// 			window.removeEventListener('mousemove', updateMousePosition);
	// 			window.removeEventListener('keydown', resetInactivityTimer);
	// 			clearTimeout(inactivityTimeout);
	// 		};
	// 	});

	// 	return {
	// 		get x() {
	// 			return x;
	// 		},
	// 		get y() {
	// 			return y;
	// 		}
	// 	};
	// };

	// const mouse = useMousePosition();

	// $effect(() => {
	// 	if (!container) return;
	// 	const rect = container.getBoundingClientRect();
	// 	const padding = 32;

	// 	if (
	// 		mouse.x < rect.left + padding ||
	// 		mouse.x > rect.right - padding ||
	// 		mouse.y < rect.top + padding ||
	// 		mouse.y > rect.bottom - padding
	// 	) {
	// 		isOutside.set(true);
	// 	} else {
	// 		isOutside.set(false);
	// 	}
	// });
</script>

<ModeWatcher />
<div
	bind:this={container}
	class={`min-h-screen  bg-grid-pattern bg-grid-small  md:bg-grid-large ${$isOutside || $isInactive ? 'bg-red-700' : 'bg-white dark:bg-secondaryBlack'}`}
>
	<ThemeSwitcher />
	{@render children()}
</div>
