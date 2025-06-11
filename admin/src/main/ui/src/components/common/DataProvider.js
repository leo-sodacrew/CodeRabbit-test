const DataProvider = (axios) => {
  const createQuery = (params) => {
    const {pagination, sort = {field: "id", order: "desc"}} = params;
    const {page, perPage} = pagination;
    const {field, order} = sort;

    return {
      sort: field + "," + order,
      page: page - 1,
      size: perPage,
      ...params.filter,
    };
  };

  return {
    getList: async (resource, params) => {
      const query = createQuery(params);
      const response = await axios.get(`/${resource}`, {params: query});
      return {
        data: response.data.content,
        total: response.data.totalElements,
      };
    },

    getOne: async (resource, params) => {
      const response = await axios.get(`/${resource}/${params.id}`);
      return {
        data: response.data,
      };
    },

    getMany: async (resource, params) => {
      const response = await axios.get(`/${resource}`, {
        params: {ids: params.ids},
      });
      return {
        data: response.data,
      };
    },

    getManyReference: async (resource, params) => {
      const query = createQuery(params);
      const response = await axios.get(`/${resource}`, {
        params: {
          ...query,
          [params.target]: params.id,
        },
      });
      return {
        data: response.data.content,
        total: response.data.totalElements,
      };
    },

    create: async (resource, params) => {
      const response = await axios.post(`/${resource}`, params.data);
      return {
        data: response.data,
      };
    },

    update: async (resource, params) => {
      const response = await axios.put(`/${resource}/${params.id}`, params.data);
      return {
        data: response.data,
      };
    },

    updateMany: async (resource, params) => {
      const response = await axios.put(`/${resource}/batch`, {
        ids: params.ids,
        data: params.data,
      });
      return {
        data: response.data,
      };
    },

    delete: async (resource, params) => {
      await axios.delete(`/${resource}/${params.id}`);
      return {
        data: params.previousData,
      };
    },

    deleteMany: async (resource, params) => {
      await axios.delete(`/${resource}/batch`, {
        data: {ids: params.ids},
      });
      return {
        data: params.ids,
      };
    },
  };
};

export default DataProvider;