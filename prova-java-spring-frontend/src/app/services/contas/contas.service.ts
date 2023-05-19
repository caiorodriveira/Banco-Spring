import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from './../../../environments/environments';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ContasService {

  private readonly API = environment.apiUrl + "contas";
  constructor(private http: HttpClient) { }

  getAll(): Observable<any[]>{
    return this.http.get<any[]>(this.API);
  }

  getExtrato(idConta: number): Observable<any[]>{
    return this.http.get<any[]>(this.API + "/" + idConta + "/extrato");
  }

  getByPessoa(idPessoa: number): Observable<any[]>{
    return this.http.get<any[]>(this.API + "/pessoa/" + idPessoa );
  }

  save(conta: any): Observable<any>{
    return this.http.post(this.API, conta);
  }

  remove(conta: number): Observable<any>{
    return this.http.delete(this.API + "/" + conta)
  }
}
