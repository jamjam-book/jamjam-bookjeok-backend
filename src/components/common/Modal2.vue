<template>
    <div v-if="visible" class="modal" tabindex="0" @keydown.enter="handleConfirm">
        <div class="modal-content">
            <p>{{ message }}</p>
            <div class="modal-buttons">
                <button class="confirm-btn" @click="handleConfirm">확인</button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { nextTick, watch } from 'vue'

const props = defineProps({
    visible: { type: Boolean, required: true },
    message: { type: String, default: "완료되었습니다." }
})
const emit = defineEmits(['close'])

function handleConfirm() {
    emit('close')
}

// 모달 열릴 때 자동 포커스
function focusModal() {
    nextTick(() => {
        const modal = document.querySelector('.modal')
        if (modal) modal.focus()
    })
}
watch(() => props.visible, (val) => {
    if (val) focusModal()
})
</script>

<style scoped>
.modal {
    position: fixed;
    display: flex;
    justify-content: center;
    align-items: center;
    z-index: 1000;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.4);
    outline: none;
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
    text-align: center;
    white-space: pre-line;
    word-break: keep-all;
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
