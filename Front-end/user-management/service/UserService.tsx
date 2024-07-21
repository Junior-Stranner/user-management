import axios from "axios";
import { BaseService } from "./BaseService";



export class UserService extends BaseService {

  constructor(){
      super("/api/v1/user");
  }

}