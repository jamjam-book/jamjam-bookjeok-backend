<script setup>
defineProps({
    label: String,
    value: String
})
</script>

<template>
    <div class="info-row">
        <div class="info-label">{{ label }}</div>
        <div class="info-value">
            <slot>{{ value }}</slot>
        </div>
    </div>
</template>

<style scoped>
.info-row {
    display: flex;
    border: 1px solid #e0e0e0;
    border-radius: 8px;
    overflow: hidden;
    font-size: 13px;
    text-align: center;
}

.info-label {
    background-color: #fef2dc;
    color: #5a3921;
    padding: 12px 16px;
    font-weight: bold;
    width: 120px;
    flex-shrink: 0;

}

.info-value {
    padding: 12px 16px;
    flex: 1;
    background-color: white;
}
</style>
