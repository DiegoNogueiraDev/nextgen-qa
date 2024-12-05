# api.py
from fastapi import FastAPI

app = FastAPI()

@app.get("/")
def read_root():
    return {"message": "API funcionando"}

@app.post("/predict")
def predict(data: dict):
    return {"prediction": "valor previsto", "input": data}
