
module.exports = {

  //nile(测试网络)
  dev: {
    //open_from TSpbRADBCTZCNNTwqzoHVQoRHyuRYiyu5y
    network    : 'nile',
    fullNode   : 'https://api.nileex.io/',
    solidityNode   : 'https://api.nileex.io',
    eventServer   : 'https://event.nileex.io/',
    fromAddress: 'TSpbRADBCTZCNNTwqzoHVQoRHyuRYiyu5y',
    toAddress  : 'TPYXWcPZ9DC9R4PvQniTPSjUegUyBD3kJJ',
    privateKey : '0d35dba8af935d575924cd0d3afd2479248de12aa0f13f547e2e9debcdd025c5'
  },

  //主网
  prod: {
    network    : 'main',
    fullNode   : 'https://api.trongrid.io/',
    solidityNode   : 'https://api.trongrid.io',
    eventServer   : 'https://event.trongrid.io/',
    fromAddress: 'TSpbRADBCTZCNNTwqzoHVQoRHyuRYiyu5y',
    toAddress  : 'TPYXWcPZ9DC9R4PvQniTPSjUegUyBD3kJJ',
    privateKey : '0d35dba8af935d575924cd0d3afd2479248de12aa0f13f547e2e9debcdd025c5'
  }

}

