import { Observable } from 'rxjs';
import { environment } from './../../../environments/environments';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MovimentacaoService {

  private readonly API = environment.apiUrl + "movimentacao"
  constructor(private http: HttpClient) { }

  saque(saque: any): Observable<any>{
    return this.http.post(this.API + "/saque", saque);
  }

  deposito(deposito: Number): Observable<any>{
    return this.http.post(this.API + "/deposito", deposito);
  }
}
