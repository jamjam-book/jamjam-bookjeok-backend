<script setup>
defineProps({
    headers: {
        type: Array,
        required: true
    },
    keyMap: {
        type: Object,
        required: true
    },
    rows: {
        type: Array,
        required: true
    }
});
</script>

<template>
        <table class="admin-table">
            <thead>
                <tr class="table-header">
                    <!-- headers의 배열을 반복해서 각 열의 헤더 생성하기 -->
                    <th v-for="(header, index) in headers" :key="index">{{ header }}</th>
                </tr>
            </thead>

            <tbody>
                <!-- 데이터 행을 넣기! 실제 데이터 값이 이곳에 들어가는 것임 row 객체를 넣은 뒤 -->
                <tr v-for="(row, rowIndex) in rows" :key="rowIndex" class="table-content">
                    <!-- 여기에서 값을 빼서 넣으면서 정의하고 있음 row["memberId"] 이런식으로 결과가 도출 되는 것!-->
                    <td
                            v-for="(header, colIndex) in headers"
                            :key="colIndex"
                    >
                        {{ row[keyMap[header]] }}
                    </td>
                </tr>
            </tbody>
        </table>
</template>


<style scoped>
.admin-table {
    width: 900px;
    /* 겹치는 테두리를 하나로 합치는 속성 */
    border-collapse: collapse;
    font-size: 16px;
}

.admin-table th,
.admin-table td {
    padding: 8px;
    text-align: center;
    background-color: #f9f0df;
    color: #522404;
}

.admin-table tbody tr {
    border-bottom: 1px solid #ddd;
}

.admin-table tbody tr td {
    background-color: #fff;
    color: #333;
}
</style>
