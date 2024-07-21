import axios from "axios";
import { BaseService } from "./BaseService";


export class ResourceService extends BaseService {

    constructor(){
        super("/recurso");
    }

}