const axios = require("axios");

class IcalService {
  static async getIcal(link) {
    try {
      const response = await axios.get(link);
      return response.data;
    } catch (error) {
      throw error;
    }
  }
}

module.exports = IcalService;
