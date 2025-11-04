export default function Grade2Table() {
  return (
    <div style={{
      fontFamily: 'Inter, sans-serif',
      width: '100%',
      maxWidth: '1900px',
      margin: '40px auto',
      background: '#fff',
      borderRadius: '12px',
      padding: '24px',
    }}>
      <h2 style={{margin: 0, fontSize: '24px', fontWeight: 600, color: '#144A3B'}}>
        Grade TESTING Students
      </h2>

 <div style={{
  marginTop: '8px',
  background: '#EAF6F0',
  display: 'flex',              
  alignItems: 'center',         
  padding: '8px 12px',         
  borderRadius: '8px',
  fontSize: '15px',
  color: '#1B6A56',
  width: 'fit-content',        
  maxWidth: '100%',             
  wordBreak: 'break-word',      
}}>
  Teacher: Mr. Robert Wilson
</div>

      <div style={{ marginTop: '20px', display: 'flex', justifyContent: 'flex-end', marginRight: '1820px' }}>
        <Frame1Button />
      </div>


<table style={{
  width: '100%',
  borderCollapse: 'collapse',
  marginTop: '24px',
  fontSize: '15px',
  tableLayout: 'fixed'
}}>
  <thead>
    <tr style={{textAlign: 'left', color: '#1B6A56', borderBottom: '1px solid #F1F7F4'}}>
      <th style={{padding: '10px', width: '5%'}}>#</th>
      <th style={{padding: '10px', width: '10%'}}>ID</th>
      <th style={{padding: '10px', width: '50%'}}>Student Name</th>
      <th style={{padding: '10px', width: '12%'}}>Attended School</th>
      <th style={{padding: '10px', width: '12%'}}>Had Lunch</th>
      <th style={{padding: '10px', width: '8%'}}>Actions</th>
    </tr>
  </thead>
  <tbody>
    <tr style={{borderBottom: '1px solid #F1F7F4'}}>
      <td style={{padding: '10px'}}>1</td>
      <td style={{padding: '10px'}}>#9</td>
      <td style={{padding: '10px'}}>Alexandra Monteverde-Howard</td>
      <td style={{padding: '10px'}}><input type="checkbox" /></td>
      <td style={{padding: '10px'}}><input type="checkbox" /></td>
      <td style={{padding: '10px'}}>🗑</td>
    </tr>
     <tr style={{borderBottom: '1px solid #F1F7F4'}}>
      <td style={{padding: '10px'}}>1</td>
      <td style={{padding: '10px'}}>#9</td>
      <td style={{padding: '10px'}}>Alexandra Monteverde-Howard</td>
      <td style={{padding: '10px'}}><input type="checkbox" /></td>
      <td style={{padding: '10px'}}><input type="checkbox" /></td>
      <td style={{padding: '10px'}}>🗑</td>
    </tr>
     <tr style={{borderBottom: '1px solid #F1F7F4'}}>
      <td style={{padding: '10px'}}>1</td>
      <td style={{padding: '10px'}}>#9</td>
      <td style={{padding: '10px'}}>Alexandra Monteverde-Howard</td>
      <td style={{padding: '10px'}}><input type="checkbox" /></td>
      <td style={{padding: '10px'}}><input type="checkbox" /></td>
      <td style={{padding: '10px'}}>🗑</td>
    </tr>
  </tbody>
</table>

    </div>
  );
}


function Frame1Button({ onClick }) {
  return (
    <button
      onClick={onClick}
      className="bg-[#d9fff5] box-border content-stretch flex flex-col items-start px-[16px] py-[18px] relative rounded-[12px] shadow-[0px_4px_4px_0px_rgba(0,0,0,0.25)] shrink-0 size-[80px] hover:bg-[#c0f5e6] active:scale-95 transition-all"
    >
      <p className="[text-shadow:rgba(0,0,0,0.25)_0px_4px_4px] font-['Inter:Regular',sans-serif] font-normal leading-[normal] not-italic text-[#2f7f6b] text-[36px] whitespace-pre">
        AS
      </p>
    </button>
  );
}

function Frame2Desktop() {
  return (
    <div className="bg-white content-stretch flex items-center relative size-full">
      <Frame1Button />
    </div>
  );
}

function Frame3Desktop() {
  return (
    <div className="bg-[#c7b7d0] relative rounded-[10px] size-full">
      <div className="flex flex-row items-center justify-center size-full">
        <div className="box-border content-stretch flex items-center justify-center p-[11px] relative size-full">
          <p className="font-['Inter:Semi_Bold',sans-serif] font-semibold leading-[normal] not-italic relative shrink-0 text-[14px] text-[rgba(0,0,0,0.5)] text-nowrap tracking-[2.52px] whitespace-pre">Add News</p>
        </div>
      </div>
    </div>
  );
}