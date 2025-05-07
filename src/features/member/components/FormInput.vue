<script setup>
import {reactive, defineProps} from 'vue';

const formData = reactive({});

/* defineProps를 이용해 부모에게서 어떤 값을 넣을지 받아오기*/
const { fields, buttonText } = defineProps({
    fields : Array,
    buttonText : {
        type: String,
        default: '제출하기' // 만약 아무것도 없는 경우 default
    }
});

</script>

<template>
    <div class="auth-container">
        <div class="input-box">
            <div
                    v-for="field in fields"
                    :key="field.id"
                    class="input-group"
            >
                <label :for="field.id" class="label">{{ field.label }}</label>
                <input
                        :id="field.id"
                        :type="field.type"
                        :placeholder="field.placeholder"
                        class="input"
                />
            </div>
            <button class="submit-button">{{ buttonText }}</button>
        </div>
    </div>
</template>

<style scoped>
.auth-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 150px 150px 192px 150px;
}

.input-box {
    max-width: 540px;
    width: 150%;
    border: 1px solid #d9d9d9;
    border-radius: 8px;
    padding: 32px 24px;
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
    display: flex;
    flex-direction: column;
    gap: 24px;
}

.input-group {
    display: flex;
    flex-direction: column;
    gap: 8px;
}

.label {
    font-size: 18px;
    color: #1e1e1e;
}

.input {
    padding: 12px 16px;
    border: 1px solid #d9d9d9;
    border-radius: 8px;
    font-size: 16px;
    color: #333;
}

input:focus {
    outline: none;
}

.input::placeholder {
    color: #b3b3b3;
}

.submit-button {
    padding: 12px;
    background-color: #f9f0df;
    border: 1px solid #d9d9d9;
    border-radius: 8px;
    font-size: 18px;
    color: #522404;
    cursor: pointer;
    transition: background-color 0.2s ease;
}

.submit-button:hover {
    background-color: #f4e7cb;
}</style>