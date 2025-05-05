<template>
    <div class="book-register-wrapper">
        <div class="book-register-container">
            <h2 class="title">도서 입고 시스템</h2>

            <div class="form-group">
                <label for="isbn">ISBN</label>
                <input
                        type="text"
                        id="isbn"
                        v-model="isbn"
                        maxlength="13"
                        placeholder="ISBN 번호를 입력해주세요."
                />
            </div>

            <button class="register-btn" @click="registerBook">도서 등록</button>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const isbn = ref('')

const registerBook = async () => {
    if (!isbn.value) {
        alert('ISBN을 입력해주세요.')
        return
    }

    try {
        const res = await axios.get(`/api/book/naver?isbn=${isbn.value}`)
        console.log('도서 정보:', res.data)

        // TODO: 여기서 폼에 채워넣거나 등록 로직 연결
        alert('도서 정보를 성공적으로 불러왔습니다.')

    } catch (err) {
        alert('도서를 찾을 수 없습니다.')
    }
}
</script>

<style scoped>
.book-register-wrapper {
    display: flex;
    justify-content: center;
    align-content: center;
    padding: 200px 0;
}

.book-register-container {
    background-color: #fef8ea;
    border-radius: 12px;
    padding: 40px;
    width: 400px;
    box-shadow: 0 2px 8px rgba(0,0,0,0.1);
    text-align: center;
}

.title {
    font-size: 22px;
    font-weight: bold;
    color: #5a3921;
    margin-bottom: 30px;
}

.form-group {
    margin-bottom: 20px;
    text-align: left;
}

label {
    font-weight: bold;
    display: block;
    margin-bottom: 6px;
    color: #5a3921;
}

input {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 8px;
    font-size: 14px;
}

.register-btn {
    margin-top: 10px;
    width: 100%;
    padding: 12px;
    background-color: #fdf1de;
    border: none;
    border-radius: 8px;
    font-weight: bold;
    cursor: pointer;
    color: #333;
    transition: 0.3s;
}

.register-btn:hover {
    background-color: #fce4bc;
}
</style>
