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
    <div class="container">
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
@import "@/assets/css/formInput.css";
</style>