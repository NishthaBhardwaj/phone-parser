function fn(){
    var env = karate.env; // get system property 'karate.env'
    karate.log('karate.env system property was:', env)
    if(!env){
        env = 'local'
    }
    var config = {
        env: env,
        myVarName: 'someValue',
        _url: 'http://34.133.58.253:9000'

  }
    if (env == 'local'){
        config.myVarName = 'local'

    } else if (env == 'cloud'){

        _url = 'http://34.133.58.253:9000'
        karate.log('karate _url:', _url)
        config.myVarName =  'cloud'

    }
    return config;


}