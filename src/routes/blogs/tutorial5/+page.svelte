<script>
	import { Button } from '$lib/components/ui/button';

	let data = $props();
	$inspect(data);

	let username = $state('');
	let isEditMode = $state(false);
	let people = $state(['Kobar', 'Doe', 'John', 'Jane']);
</script>

{#snippet userInput(username)}
	<li>User Name for person waiting:{username}</li>
{/snippet}

{#snippet form()}
	<h1>User Name:</h1>
	{#if isEditMode}
		<input type="text" bind:value={username} />
	{:else}
		<h1>Closed</h1>
	{/if}
{/snippet}

{@render form()}
<ul>
	{#each people as person}
		{@render userInput(person)}
	{/each}
</ul>

<Button onclick={() => (isEditMode = !isEditMode)}>
	{isEditMode ? 'Edit Mode' : 'View Mode'}
</Button>

{#if isEditMode && username}
	<Button
		onclick={() => {
			people.push(username);
		}}>I'm waiting too</Button
	>
{/if}
