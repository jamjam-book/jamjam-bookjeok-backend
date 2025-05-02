<template>
    <div v-if="visible" class="modal">
        <div class="modal-content">
            <p>{{ message }}</p>
            <div class="modal-buttons">
                <button class="confirm-btn" @click="handleConfirm">확인</button>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "Modal",
    props: {
        visible: {
            type: Boolean,
            required: true
        },
        message: {
            type: String,
            default: "완료되었습니다."
        }
    },
    emits: ['close'],
    methods: {
        handleConfirm() {
            this.$emit('close');
        }
    }
}
</script>

<style scoped>
.modal {
    position: fixed;
    z-index: 1000;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.4);
}

.modal-content {
    background-color: #fff;
    margin: 15% auto;
    padding: 20px;
    border-radius: 8px;
    width: 300px;
    text-align: center;
    box-shadow: 0 5px 15px rgba(0,0,0,0.3);
}

.modal-content p {
    color: #391902;
    font-size: 16px;
    margin-bottom: 20px;
}

.modal-buttons {
    display: flex;
    justify-content: center;
}

.confirm-btn {
    padding: 6px 16px;
    font-size: 12px;
    border: 1px solid #000;
    border-radius: 4px;
    cursor: pointer;
    background-color: #6d381a;
    color: #fff;
}
</style>
