import axios from "axios";

const api = axios.create({
    baseURL : 'http://localhost:8080/api/v1/members'
});

/* 관심 작가*/
export const getInterestAuthors = (params, memberId) =>
    api.get(`/${memberId}/interest/authors`, { params });

export const createInterestAuthors = ( payload ) =>
    api.post(`/interest/authors`, payload );

export const deleteInterestAuthors = (memberId, authorId) =>
    api.delete(`/${memberId}/interest/authors/${authorId}`)

/* 팔로잉 */
export const getFollowings = memberId => api.get(`/${memberId}/followings`)

export const deleteFollowings = (followerId, followingId) =>
    api.delete(`/${followerId}/followings/${followingId}`)