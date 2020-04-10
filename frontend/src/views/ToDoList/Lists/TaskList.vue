<template>
    <div class="col-3 list-column list-width">
        <div class="heading" style="background-color: rgb(96, 125, 139);">
        <h4 class="heading-text text-center">
            {{ list.name }}
        </h4>
        </div>
    </div>
</template>
<script>
export default {
    name: 'TaskList',
    props: ["list"]
}
</script>