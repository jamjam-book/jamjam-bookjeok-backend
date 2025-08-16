export const bookRoutes = [
    {
        path : '/books',
        name : 'BookList',
        component : () => import('@/features/book/views/BookListView.vue')
    },
    {
        path : '/books/:bookId',
        name : 'BookDetail',
        component : () => import('@/features/book/views/BookDetailView.vue')
    },
    {
        path : '/books/authorbooks',
        name : 'AuthorBookList',
        component : () => import('@/features/book/views/AuthorBookListView.vue')

    },
    {
        path : '/books/publisherbooks',
        name : 'PublisherBookList',
        component : () => import('@/features/book/views/PublisherBookListView.vue')

    }
];