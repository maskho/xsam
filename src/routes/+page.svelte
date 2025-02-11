<script>
	import { page } from '$app/state';
	import { Button } from '$lib/components/ui/button';
	import * as Card from '$lib/components/ui/card';

	import { ArrowDown } from 'lucide-svelte';

	function scrollToTutorial() {
		window.scrollTo({
			top: window.innerHeight,
			behavior: 'smooth'
		});
	}
</script>

<header class="inset-0 flex min-h-screen w-full flex-col items-center justify-center">
	<Card.Root class="w-[350px] md:w-[500px]">
		<Card.Header>
			<Card.Title>
				<h1 class="text-3xl font-heading lg:text-4xl">Testing</h1>
			</Card.Title>
			<Card.Description>
				Get started with your saas landing page in minutes.<br />
				Check the github repo for more info.
			</Card.Description>
		</Card.Header>
		<Card.Content>
			<Button size="lg" href="/dashboard">Buat Baru</Button>
		</Card.Content>
	</Card.Root>

	<Button size="default" class="absolute bottom-10">
		Lihat Tutorial <ArrowDown />
	</Button>
</header>
