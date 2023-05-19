import { environment } from './../../../environments/environments';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PessoasService {

  private readonly API = environment.apiUrl + "pessoas";
  constructor(private http: HttpClient) { }

  getAll(): Observable<any[]>{
    return this.http.get<any[]>(this.API);
  }
  getById(idPessoa: number): Observable<any[]>{
    return this.http.get<any[]>(this.API + "/" + idPessoa);
  }

  save(pessoa: any): Observable<any>{
    return this.http.post(this.API, pessoa);
  }

  remove(idPessoa: number): Observable<any>{
    return this.http.delete(this.API + "/" + idPessoa)
  }
}
