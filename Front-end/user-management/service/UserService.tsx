import User from "@/app/(main)/pages/user/page";
import axios from "axios";


export const axiosInstance = axios.create({
  baseURL: "http://localhost:8080"
})

export class UserService{
   listarTodos(){
    return axiosInstance.get("/api/v1/user");
   }

   inserir(user : Projeto.User){
    return axiosInstance.post("/api/v1/user", user);
   }

   alterar(user : Projeto.User){
    return axiosInstance.put("/api/v1/user", user);
   }

   excluir(id : number){
     return axiosInstance.delete("/api/v1/user/"+id);
   }

}