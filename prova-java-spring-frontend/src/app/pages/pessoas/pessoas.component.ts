import { PessoasService } from './../../services/pessoas/pessoas.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-pessoas',
  templateUrl: './pessoas.component.html',
  styleUrls: ['./pessoas.component.css']
})

export class PessoasComponent implements OnInit{
  pessoaForm: FormGroup;
  messageError: any;
  dataPessoas = new MatTableDataSource<any>()
  displayedColumns = ['registro', 'nome', 'endereco', 'editar', 'remover'];
  pessoaAtual: any;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(
    private fb: FormBuilder,
    private pessoaService: PessoasService
  ) {
    this.pessoaForm = fb.group({
      id: [null],
      nome: [null, [Validators.required]],
      cpf: [null, [Validators.required]],
      endereco: [null, [Validators.required]],
    })

    this.pessoaAtual = {
      "id" : null,
      "nome":null,
      "cpf":null,
      "endereco":null
    }
  }
  ngAfterViewInit(): void {
    this.dataPessoas.paginator = this.paginator;
  }


  ngOnInit(): void {
    this.listarPessoas();
  }

  salvarPessoa(pessoa: any) {
    if(pessoa.nome[0] != (pessoa.nome[0]).toUpperCase()){
      this.messageError = {
        "erro": "Erro ao enviar as informações",
        "motivos": ["A primeira letra do nome deve ser maiúscula"]
      }
    } else {
    this.pessoaService.save(pessoa).subscribe(
      (res) => {
        console.log(res);
        this.pessoaForm.reset();
        window.location.reload();
      },
      (error) => {
        this.messageError = {
          "erro": "Erro ao enviar as informações",
          "motivos": {}
        }
        if (error.status == 400) {
          this.messageError.motivos = [
            "verifique os campos se estão preenchidos corretamente",
            "verifique se ja não há um CPF cadastrado"
          ]
        }
      })
    }
  }

  listarPessoas(){
    this.pessoaService.getAll().subscribe(
      (res) => {
        this.dataPessoas.data = res;
      }
    )
  }

  confirmarRemocao(pessoa: any){
    this.pessoaAtual = pessoa;
  }

  removerPessoa(pessoa: any){
    this.pessoaService.remove(pessoa.id).subscribe(
      () => {window.location.reload()},
      (error) =>{
        this.messageError = {
          "erro": "Erro",
          "motivos": ["não foi possivel remover " +pessoa.nome ]
        }
      }
    )
  }

  editarPessoa(pessoa:any){
    this.pessoaForm.patchValue({
      id: pessoa.id,
      nome: pessoa.nome,
      cpf: pessoa.cpf,
      endereco: pessoa.endereco
    })
  }

}
