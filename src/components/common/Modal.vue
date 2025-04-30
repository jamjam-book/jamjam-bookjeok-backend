<template>
  <div v-if="visible" class="modal">
    <div class="modal-content">
      <p>{{ message }}</p>
      <div class="modal-buttons">
        <button class="cancel-btn" @click="emit('cancel')">취소</button>
        <button class="confirm-btn" @click="emit('confirm')">확인</button>
      </div>
    </div>
  </div>
</template>

<script setup>
defineProps({
  visible: {
    type: Boolean,
    required: true
  },
  message: {
    type: String,
    default: '정말 진행하시겠습니까?'
  }
})

const emit = defineEmits(['confirm', 'cancel'])
</script>

<style scoped>
.modal {
  position: fixed;
  z-index: 1000; /* 모달이 다른 요소보다 위에 보이도록 */
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
  justify-content: space-evenly;
}

.modal-buttons button {
  padding: 6px 16px;
  font-size: 12px;
  border: 1px solid #000;
  border-radius: 4px;
  cursor: pointer;
}

.cancel-btn {
  background-color: #f9f0df;
  color: #000;
}

.confirm-btn {
  background-color: #6d381a;
  color: #fff;
}
</style>