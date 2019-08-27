# app.py  - a minimal flask api using flask_restuful

from flask import Flask
from flask_restful import Resource, Api

app = Flask(__name__)
api = Api(app)

class HelloElastest(Resource):
    def get(self):
        return {'hello':'elastest'}
api.add_resource(HelloElastest, '/')

if __name__=='__main__':
    app.run(debug=True, host='0.0.0.0')


