<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

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

const goToMember = (memberId) => {
    router.push(`/admin/members/${memberId}`);
};
</script>

<template>
    <table class="admin-table">
        <thead>
            <tr class="table-header">
                <th v-for="(header, index) in headers" :key="index">{{ header }}</th>
            </tr>
        </thead>

        <tbody>
            <tr
                    v-for="(row, rowIndex) in rows"
                    :key="rowIndex"
                    class="table-link"
                    @click="goToMember(row.memberId)"
            >
                <td v-for="(header, colIndex) in headers" :key="colIndex">
                    {{ row[keyMap[header]] }}
                </td>
            </tr>
        </tbody>
    </table>
</template>


<style scoped>
.admin-table {
    width: 900px;
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

.table-link {
    cursor: pointer;
}
</style>
