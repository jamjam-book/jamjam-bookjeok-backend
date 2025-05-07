<template>
    <div class="spinner"></div>
</template>

<style scoped>
.spinner {
    width: 32px;
    height: 32px;
    border: 4px solid #f3f3f3;
    border-top: 4px solid #854d14;
    border-radius: 50%;
    animation: spin 0.7s linear infinite;
}

@keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
}
</style>
