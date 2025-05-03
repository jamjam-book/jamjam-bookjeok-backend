<template>
  <div class="inquiry-form">

    <!-- 카테고리 선택 -->
    <div class="form-group">
      <label class="label-category">카테고리</label>
      <QuestionCategories
          :categories="categories"
          v-model="categoryId"
      />
      <p v-if="categoryError" class="error">{{ categoryError }}</p>
    </div>

    <!-- 제목 입력 -->
    <div class="form-group">
      <label class="label">제목</label>
      <input
          type="text"
          class="input"
          v-model="title"
          :placeholder="isEdit ? '' : '제목을 입력해주세요.'"
      />
      <p v-if="titleError" class="error">{{ titleError }}</p>
    </div>

    <!-- 내용 입력 -->
    <div class="form-group">
      <textarea
          class="textarea"
          v-model="content"
          :placeholder="isEdit ? '' : '문의하실 내용을 입력해주세요.'"
      ></textarea>
      <p v-if="contentError" class="error">{{ contentError }}</p>
    </div>

    <!-- 이미지 첨부 -->
    <div class="form-group file-upload">
      <label class="file-label" for="file">이미지 첨부하기</label>
      <input id="file" type="file" @change="handleFileChange" hidden />
      <span class="filename">{{ fileName }}</span>
    </div>

    <!-- 이미지 미리보기 -->
    <div v-if="imagePreview" class="image-preview">
      <img :src="getImageSrc()" alt="미리보기 이미지" />
      <button class="delete-button" @click="removeImage">삭제</button>
    </div>

    <!-- 제출 버튼 -->
    <div class="form-group submit-button">
      <button @click="submitForm">제출하기</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useForm, useField } from 'vee-validate';
import * as yup from 'yup';
import {useRouter} from "vue-router";
import QuestionCategories from "@/features/question/components/QuestionCategories.vue";

const router = useRouter();

const props = defineProps({
  isEdit: { type: Boolean, default: false },
  initialData: { type: Object, default: () => ({}) },
});

const categories = [
  { id: 1, name: '주문' },
  { id: 2, name: '회원' },
  { id: 3, name: '배송' }
];

const schema = yup.object({
  title: yup.string().required('제목은 필수입니다.'),
  content: yup.string().required('내용을 입력해주세요.'),
  categoryId: yup.string().required('카테고리를 선택해주세요.'),
});

const { handleSubmit } = useForm({ validationSchema: schema });

const { value: title,errorMessage: titleError } = useField('title');
const { value: content, errorMessage: contentError } = useField('content');
const { value: categoryId, errorMessage: categoryError } = useField('categoryId');

const form = ref({
  title: '',
  content: '',
  categoryId: null,
  imageFile: null,
});
const fileName = ref('');
const imagePreview = ref('');
const imageFile = ref(null);

onMounted(() => {
    if (props.isEdit && props.initialData) {
        title.value = props.initialData.title;
        content.value = props.initialData.content;
        categoryId.value = props.initialData.categoryId;
        fileName.value = props.initialData.imageFileName || '';
        imagePreview.value = props.initialData.imageUrl || '';
    }
});


const getImageSrc = () => {
  const preview = imagePreview.value;

  if (!preview) return ''; // 아무것도 없으면 빈 문자열 반환

  if (preview.startsWith('blob:')) {
    return preview;
  } else if (preview.startsWith('/')) {
    return preview;
  } else {
    return `/images/${preview}`;
  }
};

const handleFileChange = (e) => {
  const file = e.target.files[0];
  if (file) {
    imageFile.value = file;
    fileName.value = file.name;
    imagePreview.value = URL.createObjectURL(file);
  }
};

const removeImage = () => {
  form.value.imageFile = null;
  fileName.value = '';
  imagePreview.value = '';
  document.getElementById('file').value = null;
};

const submitForm = handleSubmit((values) => {
    const formData = new FormData();
    formData.append('title', title.value);
    formData.append('content', content.value);
    formData.append('categoryId', categoryId.value);
    if (imageFile.value) {

        formData.append('file', imageFile.value);

    }

  if (props.isEdit) {
    console.log('수정 요청', formData);
  } else {
    console.log('등록 요청', formData);
  }
  router.back();
});
</script>

<style scoped>
.inquiry-form {
  max-width: 600px;
  padding: 24px;
  border: 1px solid #ddd;
  border-radius: 12px;
  font-family: sans-serif;
}

.form-group {
  margin-bottom: 20px;
}

.label, .label-category {
  display: inline-block;
  background-color: #fdf2e9;
  padding: 10px 16px;
  border-radius: 8px 0 0 8px;
  font-weight: bold;
  color: #5a3921;
}
.label-category {
  border-radius: 8px;
}

.input {
  width: calc(100% - 70px);
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 0 8px 8px 0;
}

.textarea {
  width: 100%;
  height: 180px;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 16px;
  resize: vertical;
}

.file-upload {
  display: flex;
  align-items: center;
  gap: 12px;
}

.file-label {
  background-color: #fdf2e9;
  padding: 6px 12px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
}

.filename {
  font-size: 14px;
  color: #555;
}

.image-preview {
  margin-top: 12px;
}

.image-preview img {
  max-width: 200px;
  max-height: 200px;
  border-radius: 8px;
  border: 1px solid #ccc;
}

.delete-button {
  display: block;
  margin-top: 8px;
  background-color: #f8d7da;
  color: #721c24;
  border: none;
  border-radius: 6px;
  padding: 6px 12px;
  font-size: 13px;
  cursor: pointer;
}

.submit-button {
  text-align: right;
}

.submit-button button {
  background-color: #fdf2e9;
  border: none;
  padding: 8px 16px;
  border-radius: 8px;
  cursor: pointer;
  font-size: 14px;
}

.error {
  color: red;
  font-size: 12px;
  margin-top: 4px;
}
</style>
