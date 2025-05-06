<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const formatPhoneNumber = (number) => {
    if (!number) return '';
    return number
            .replace(/[^0-9]/g, '')
            .replace(/^(\d{3})(\d{3,4})(\d{4})$/, '$1-$2-$3');
};

const formatDate = (datetime) => {
    if (!datetime) return '';
    return datetime.split('T')[0];
};


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
                    <template v-if="keyMap[header] === 'phoneNumber'">
                        {{ formatPhoneNumber(row[keyMap[header]]) }}
                    </template>
                    <template v-else-if="keyMap[header] === 'createdAt'">
                        {{ formatDate(row[keyMap[header]]) }}
                    </template>
                    <template v-else-if="keyMap[header] === 'marketingConsent'">
                        {{ row[keyMap[header]] ? 'Y' : 'N' }}
                    </template>
                    <template v-else>
                        {{ row[keyMap[header]] }}
                    </template>
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
